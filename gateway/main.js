var req= require("./request");
var port=require("./port");
req.request_gateway();
setInterval(req.request_gateway, 5000);
console.log("网关已上线");
port.PortOpen();