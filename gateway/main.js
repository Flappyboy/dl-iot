var req= require("./request");
var port=require("./port");
req.request_gateway();
console.log("网关已上线");
port.PortOpen();