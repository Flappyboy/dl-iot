var mqtt = require('mqtt');
// var client = mqtt.connect('mqtt://storymap.jach.top:8031', {
//     username: 'admin',
// 	password: '1832079HH1832080',
// 	clientId: 'clientmqttiotgateway'
// });
var client = mqtt.connect('mqtt://localhost:61613', {
    username: 'admin',
	password: 'password',
	clientId: 'clientmqttiotgateway'
});
var mqttconnect = false;
client.on('connect', function() {
	mqttconnect = true;
	// client.publish('records', JSON.stringify(a), {
	// 	qos: 1,
	// 	retain: true
	// });

	// client.end();
});
//建立连接
function request_record(record) {
	var doubleRecordList = [];

	for(var i = 0; i < record.length; i++) {
		var a = {
			sensorId: record[i].sensor.id,
			unit: record[i].unit,
			mean: record[i].mean,
			recordList: record[i].recordList
		}
		doubleRecordList.push(a);
	}
	var data = {
		doubleRecords: doubleRecordList
	};
	if(mqttconnect){
		client.publish('records', JSON.stringify(data), {
			qos: 1,
			retain: true
		});
	}
	
}
module.exports = {
	request_record
}