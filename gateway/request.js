var request = require("request");
var base = 'http://storymap.jach.top:8003/api';
// base = "http://localhost:8004/api"
var url_gateway = base + '/gateway';
var url_device = base + '/device';
var url_sensor = base + '/sensor';
var url_record = base + '/record/list';

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
		if(!error && response.statusCode == 200) {

		}
		
	});
}

function request_device(device,state) {
	request({
		url: url_device,
		method: 'POST',
		json: true,
		headers: {
			"content-type": "application/json",
		},
		body: {
			"id": device.id,
			"state": state,
			"type": device.type,
			"gateway": {
				"id": device.gateway.id
			}
		}
	}, function(error, response, body) {
		if(!error && response.statusCode == 200) {}
	});
}

function request_sensor(sensor,state) {
	request({
		url: url_sensor,
		method: 'POST',
		json: true,
		headers: {
			"content-type": "application/json",
		},
		body: {
			"id": sensor.id,
			"state": state,
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
	// return;
	// console.log(record);
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
		if(error){
			console.log(error);
		}
	});
}
module.exports = {
	request_gateway,
	request_device,
	request_sensor,
	request_record
}