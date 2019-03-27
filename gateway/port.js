var SerialPort = require("serialport");
var req = require("./request");
var req_mqtt = require("./request_mqtt");
var portName = 'COM3'; //定义串口名

var ultrasonic_record = [{
	timestamp: 0,
	value: 0
}];
var temperature_record = [{
	timestamp: 0,
	value: 0
}];
var humidity_record = [{
	timestamp: 0,
	value: 0
}];
var photoresistor_record = [{
	timestamp: 0,
	value: 0
}];

var gateway = {
	id: "GateWay_1"
};
var device = {
	id: "arduino_0",
	type: "arduino",
	gateway: gateway
};
var sensor1 = {
	id: "HC-SR04_0",
	type: "HC-SR04",
	device: device
};
var sensor2 = {
	id: "DHT11_0",
	type: "DHT11",
	device: device
};
var sensor3 = {
	id: "LightSensor_0",
	type: "LightSensor",
	device: device
};
var record1 = [{
	sensor: sensor1,
	recordList: [],
	unit: "cm",
	mean: "distance"
}];
var record2 = [{
	sensor: sensor2,
	recordList: [],
	unit: "℃",
	mean: "temperature"
}, {
	sensor: sensor2,
	recordList: [],
	unit: "%",
	mean: "humidity"
}];
var record3 = [{
	sensor: sensor3,
	recordList: [],
	unit: "lx",
	mean: "photoresistor"
}];
var switchs = [0, 0, 0, 0]; //设备上线开关

var serialPort = new SerialPort(
	"COM3", {
		autoOpen: false,
		baudRate: 9600, //波特率
		dataBits: 8, //数据位
		parity: 'none', //奇偶校验
		stopBits: 1, //停止位
		flowControl: false
	}, false);
var d = '';


function PortOpen() {

	serialPort.open(function(error) {
		if(error) {
			console.log("打开端口" + portName + "错误：" + error);
			setTimeout(PortOpen, 5000);
		} else {
			console.log("打开端口成功，正在监听数据中");
			req.request_device(device);
			console.log("设备已连接");
			
			serialPort.on('data', function(data) {
				// console.log(data);
				d += data.toString('utf8');
				var dd = d.split('\n');
				d = dd[dd.length - 1];
				dd.pop();

				for(var i = 0; i < dd.length; i++) {
					var sub_dd = dd[i].split(':');
					var o = {
						timestamp: Date.parse(new Date()).toString(),
						value: sub_dd[1]
					};
					switch(sub_dd[0]) {
						case "Distance":
							{
								if(switchs[1] == 0 && o.value > 0.00) {
									req.request_sensor(sensor1,"ONLINE");
									switchs[1] = 1;
									console.log("超声波传感器已上线");
								}
								if(switchs[1]== 1&& o.value<=0.00){
									req.request_sensor(sensor1,"OFFLINE");
									switchs[1] = 0;
									console.log("超声波传感器已下线");									
								}
								ultrasonic_record.push(o);
								break;
							}
						case "Tempeature":
							{
								if(switchs[2] == 0 && o.value != 0) {
									req.request_sensor(sensor2,"ONLINE");
									switchs[2] = 1;
									console.log("温湿度传感器已上线");
								}
								if(switchs[2] == 1 && o.value == 0) {
									req.request_sensor(sensor2,"OFFLINE");
									switchs[2] = 0;
									console.log("温湿度传感器已下线");
								}
								temperature_record.push(o);
								break;
							}
						case "Humidity":
							{
								humidity_record.push(o);
								break;
							}
						case "LightSensor":
							{
								if(switchs[3] == 0 && o.value > 0) {
									req.request_sensor(sensor3,"ONLINE");
									switchs[3] = 1;
									console.log("光敏传感器已上线");
								}
								
								photoresistor_record.push(o);
								break;
							}
					}
				}

				if(photoresistor_record[photoresistor_record.length - 1].value < 600) {
					//				console.log("111")
					serialPort.write('1');
				} else {
					//				console.log("000")
					serialPort.write('0');
				}
				if(ultrasonic_record[ultrasonic_record.length - 1].value < 20.00) {
					//				console.log("222")
					serialPort.write('2');
				} else {
					//				console.log("333")
					serialPort.write('3');
				}

			});
					
		}
	});
}

function refreshCount(record, index, subindex) {
	return function concentre() {

		if(switchs[index] == 1) {

			var s = [];
			var t = record[0].timestamp;
			var count = 1;
			var sum = parseFloat(record[0].value);
			for(var i = 1; i < record.length; i++) {
				if(record[i].timestamp == t) {
					sum += parseFloat(record[i].value);
					count++;
				} else {
					var o = {
						timestamp: t,
						value: sum / count
					};
					s.push(o);
					sum = parseFloat(record[i].value);
					count = 1;
					t = record[i].timestamp;
				}
			}
			var o = {
				timestamp: t,
				value: sum / count
			};
			s.push(o);
			
			eval("record" + index)[subindex].recordList = s.slice(0, -1);
			if(index < 3) req.request_record(eval("record" + index));
			else req_mqtt.request_record(eval("record" + index));
			s.splice(0,s.length);
			record.splice(0, record.length - 1);
		}
	}
}
setInterval(refreshCount(ultrasonic_record, 1, 0), 3000);
setInterval(refreshCount(temperature_record, 2, 0), 3000);
setInterval(refreshCount(humidity_record, 2, 1), 3000);
setInterval(refreshCount(photoresistor_record, 3, 0), 3000);

module.exports = {
	PortOpen
}