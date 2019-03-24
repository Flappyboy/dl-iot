var request = require("request");
var url_gateway = 'http://storymap.jach.top:8003/api/gateway';
var url_device = 'http://storymap.jach.top:8003/api/device';
var url_sensor = 'http://storymap.jach.top:8003/api/sensor';
var url_record = 'http://storymap.jach.top:8003/api/record/list';

function request_gateway() {
	request({
		url: url_gateway,
		method: 'POST',
		json: true,
		headers: {
			"content-type": "application/json",
		},
		body: {
			"id": "GateWay_1",
			"state": "ONLINE"
		}
	}, function(error, response, body) {
		if(!error && response.statusCode == 200) {}
	});
}

function request_device(device) {
	request({
		url: url_device,
		method: 'POST',
		json: true,
		headers: {
			"content-type": "application/json",
		},
		body: {
			"id": device.id,
			"state": "ONLINE",
			"type": device.type,
			"gateway": {
				"id": device.gateway.id
			}
		}
	}, function(error, response, body) {
		if(!error && response.statusCode == 200) {}
	});
}

function request_sensor(sensor) {
	request({
		url: url_sensor,
		method: 'POST',
		json: true,
		headers: {
			"content-type": "application/json",
		},
		body: {
			"id": sensor.id,
			"state": "ONLINE",
			"type": sensor.type,
			"device": {
				"id": sensor.device.id
			}
		}
	}, function(error, response, body) {
		if(!error && response.statusCode == 200) {}
	});
}

function request_record(record) {
	var doubleRecordList=[];
	for(var i=0;i<record.length;i++)
	{
		var a={sensorId:record[i].sensor.id,unit:record[i].unit,mean:record[i].mean,recordList:record[i].recordList}
		doubleRecordList.push(a);
	}
	request({
		url: url_record,
		method: 'POST',
		json: true,
		headers: {
			"content-type": "application/json",
		},
		body: {
			"doubleRecords":doubleRecordList
		}
		
	}, function(error, response, body) {
		if(!error && response.statusCode == 200) {}
	});
}
module.exports = {
	request_gateway,
	request_device,
	request_sensor,
	request_record
}