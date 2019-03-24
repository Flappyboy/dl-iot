var mqtt = require('mqtt');
var client = mqtt.connect('mqtt://storymap.jach.top:8031', {
//	username: 
//	password: 
//	clientId: 
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
	var a = {
		doubleRecords: doubleRecordList
	};
	client.on('connect', function() {

		client.publish('records', JSON.stringify(a), {
			qos: 1,
			retain: true
		});

		client.end();
	});
}
module.exports = {
	request_record
}