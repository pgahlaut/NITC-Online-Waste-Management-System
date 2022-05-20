const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const postRequest = require('../model/request');
const Task = require('../model/task');

const taskR = express.Router();
taskR.use(bodyParser.json());

taskR.post('/',async (req,res)=>{
    const req_id =req.body.req_id;
    const request = await postRequest.findOne({_id:req_id}).lean();
    if(!request){
        return res.json({"status":"error","error":"No such request found"});
    }
    //Add in task collection
    const title =request.title;
    const location  = request.location;
    const response = await Task.create({
        title,
        location
    });
    
    console.log("Added task ",response);
    // console.log("Deleting "+request.title);
    await postRequest.findOneAndUpdate({_id:req_id},{status:"Approved"},{new:true});
    return res.json({"status":"ok"});
});

module.exports = taskR;