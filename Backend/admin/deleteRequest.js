const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const postRequest = require('../model/request');

const deleteR = express.Router();
deleteR.use(bodyParser.json());

deleteR.post('/',async (req,res)=>{
    //Now we dont delete but just update the status value to declined
    const req_id =req.body.req_id;
    const request = await postRequest.findOne({_id:req_id}).lean();
    if(!request){
        return res.json({"status":"error","error":"No such request found"});
    }
    console.log("Updating "+request.title);
    await postRequest.findOneAndUpdate({_id:req_id},{
        status:"Declined"
    });
    return res.json({"status":"ok"});
});

module.exports = deleteR;