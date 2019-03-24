#include <Arduino_FreeRTOS.h>
#include <semphr.h>
#include <dht11.h> 
#include <queue.h>

#define TrigPin 2
#define EchoPin 3
#define DhtPin 7
#define buzzerPin 12
#define ledPin 13
float cm_now,cm_last=-1,a=0.7;
dht11 DHT11;  
SemaphoreHandle_t xSerialSemaphore;
//QueueHandle_t xQueue;
void TaskTemHumRead( void *pvParameters );
void TaskLightSensorRead( void *pvParameters );
void TaskDistenceRead( void *pvParameters );
void TaskLightBlink(void *pvParameters);
void TaskBuzzerRing(void *pvParameters);
void setup() {
  Serial.begin(9600);
//xQueue = xQueueCreate(20, sizeof(uint32_t));
//if(xQueue == 0)
//  {
//    Serial.println("Queue fail");
//  }
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB, on LEONARDO, MICRO, YUN, and other 32u4 based boards.
  }
  if ( xSerialSemaphore == NULL )  // Check to confirm that the Serial Semaphore has not already been created.
  {
    xSerialSemaphore = xSemaphoreCreateMutex();  // Create a mutex semaphore we will use to manage the Serial Port
    if ( ( xSerialSemaphore ) != NULL )
      xSemaphoreGive( ( xSerialSemaphore ) );  // Make the Serial Port available for use, by "Giving" the Semaphore.
  }
  xTaskCreate(
    TaskDistenceRead
    ,  (const portCHAR *)"DistenceRead"  // A name just for humans
    ,  128  // This stack size can be checked & adjusted by reading the Stack Highwater
    ,  NULL
    ,  2  // Priority, with 3 (configMAX_PRIORITIES - 1) being the highest, and 0 being the lowest.
    ,  NULL );
  xTaskCreate(
    TaskTemHumRead
    ,  (const portCHAR *)"TemHumRead"  // A name just for humans
    ,  128  // This stack size can be checked & adjusted by reading the Stack Highwater
    ,  NULL
    ,  2  // Priority, with 3 (configMAX_PRIORITIES - 1) being the highest, and 0 being the lowest.
    ,  NULL );
  
  xTaskCreate(
    TaskLightSensorRead
    ,  (const portCHAR *)"LightSensorRead"
    ,  128  // Stack size
    ,  NULL
    ,  2  // Priority
    ,  NULL );
  xTaskCreate(
    TaskLightBlink
    ,  (const portCHAR *)"LightBlink"
    ,  128  // Stack size
    ,  NULL
    ,  2  // Priority
    ,  NULL );
  xTaskCreate(
    TaskBuzzerRing
    ,  (const portCHAR *)"BuzzerRing"  // A name just for humans
    ,  128  // This stack size can be checked & adjusted by reading the Stack Highwater
    ,  NULL
    ,  2  // Priority, with 3 (configMAX_PRIORITIES - 1) being the highest, and 0 being the lowest.
    ,  NULL );
}

void loop()
{
// Empty. Things are done in Tasks.
}

/*--------------------------------------------------*/
/*---------------------- Tasks ---------------------*/
/*--------------------------------------------------*/
void TaskDistenceRead(void *pvParameters)
{
  pinMode(TrigPin, OUTPUT); 
  pinMode(EchoPin, INPUT); 
  for(;;)
  {
    digitalWrite(TrigPin, LOW); 
    delayMicroseconds(2); 
    digitalWrite(TrigPin, HIGH); 
    delayMicroseconds(10); 
    digitalWrite(TrigPin, LOW); 
    cm_now = pulseIn(EchoPin, HIGH);
    cm_now = cm_now*17/1000;
//    Serial.print("采样值:");
//    Serial.print(cm_now);
//    Serial.print("cm\n");
    if(cm_last!=-1)
    {
      cm_now=(1-a)*cm_now+a*cm_last;
      if ( xSemaphoreTake( xSerialSemaphore, ( TickType_t ) 5 ) == pdTRUE )
      {
        Serial.print("Distance:");
        Serial.println(cm_now);
//        Serial.println(",");
//        Serial.print("cm\n");
        xSemaphoreGive( xSerialSemaphore );
      }
    }
    cm_last=cm_now;
    
    vTaskDelay(1);    
  }
}
void TaskTemHumRead( void *pvParameters)  // This is a Task.
{
  pinMode(DhtPin,OUTPUT);  
  
  for (;;) // A Task shall never return or exit.
  {     
    
    int chk = DHT11.read(DhtPin);                 //将读取到的值赋给chk

    int tem=(float)DHT11.temperature;               //将温度值赋值给tem
    int hum=(float)DHT11.humidity;                   //将湿度值赋给hum
    if ( xSemaphoreTake( xSerialSemaphore, ( TickType_t ) 5 ) == pdTRUE )
    {    
      Serial.print("Tempeature:");                        //打印出Tempeature:
      Serial.println(tem);                                     //打印温度结果
//      Serial.println(",");
      Serial.print("Humidity:");                            //打印出Humidity:
      Serial.println(hum);                                     //打印出湿度结果
//      Serial.println(",");
//      Serial.println("%");  
      xSemaphoreGive( xSerialSemaphore );
    }     
    vTaskDelay(1);  // one tick delay (15ms) in between reads for stability
  }
}

void TaskLightSensorRead( void *pvParameters )  // This is a Task.
{
  
  for (;;)
  {    
    int val = analogRead(A0);
//    xQueueSend(xQueue, (void *)&val, (TickType_t)10);
    if ( xSemaphoreTake( xSerialSemaphore, ( TickType_t ) 5 ) == pdTRUE )
    {
      Serial.print("LightSensor:"); 
      Serial.println(val);
//      Serial.println(",");
      xSemaphoreGive( xSerialSemaphore );
    }
   
    vTaskDelay(1);
  }
}
//const TickType_t xMaxBlockTime = pdMS_TO_TICKS(3000);
void TaskLightBlink( void *pvParameters )  
{
  pinMode(ledPin,OUTPUT);
  for(;;){ 
    if ( xSemaphoreTake( xSerialSemaphore, ( TickType_t ) 5 ) == pdTRUE )
    {
      while(Serial.available() > 0)     //当Serial.available()>0时，说明串口接收到了数据，可以读取。     
      {
        char flag=Serial.read();
        if(flag=='1')digitalWrite(ledPin,HIGH);
        else if(flag=='0') digitalWrite(ledPin,LOW);    
      } 
     xSemaphoreGive( xSerialSemaphore );
    }   
  }
}
void TaskBuzzerRing( void *pvParameters )  
{
    pinMode(buzzerPin,OUTPUT);
    digitalWrite(buzzerPin,HIGH);   
    for(;;)
    {
      if ( xSemaphoreTake( xSerialSemaphore, ( TickType_t ) 5 ) == pdTRUE )
      {
        while(Serial.available() > 0)     //当Serial.available()>0时，说明串口接收到了数据，可以读取。     
        {
          char flag=Serial.read();
          if(flag=='2'){           
              digitalWrite(buzzerPin,LOW);
          }
         else if(flag=='3')digitalWrite(buzzerPin,HIGH);              
        }
        xSemaphoreGive( xSerialSemaphore );
      }  
    }
}
