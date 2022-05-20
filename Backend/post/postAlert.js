const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const alertRequest = require('../model/alert');

const alertReq = express.Router();
alertReq.use(bodyParser.json());

alertReq.post('/',async (req,res)=>{
    const dustbin_id = req.body.dustbin_id;
    const alert = req.body.alert;
    const uuid = req.body.uuid;
    console.log(dustbin_id);
    console.log("Adding alert");
    try{ 
        const resp = await alertRequest.create({
            uuid,
            dustbin_id,
            alert
        });
        console.log("Alert added successfully by user ",uuid);
        res.json({status:"ok"});
    }catch(err){
        console.log("Error in creating an Alert");
        return res.json({status:'error',error:'Error in creating an Alert!'});
    }

});

module.exports = alertReq;