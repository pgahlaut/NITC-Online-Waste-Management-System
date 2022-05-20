const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const postTask = require('../model/task');

const deleteT = express.Router();
deleteT.use(bodyParser.json());

deleteT.post('/',async (req,res)=>{
    const req_id =req.body.task_id;
    const request = await postTask.findOne({_id:req_id}).lean();
    if(!request){
        return res.json({"status":"error","error":"No such request found"});
    }
    console.log("Deleting "+request.title);
    await postTask.deleteOne({_id:req_id});
    return res.json({"status":"ok"});
});

module.exports = deleteT;