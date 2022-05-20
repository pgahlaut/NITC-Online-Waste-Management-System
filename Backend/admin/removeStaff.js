const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const postRequest = require('../model/request');

const deleteS = express.Router();
deleteS.use(bodyParser.json());

deleteS.post('/',async (req,res)=>{
    const uuid =req.body.uuid;
    console.log(uuid);
    const staff = await User.findOne({"uuid":uuid}).lean();
    if(!staff){
        console.log("no such account found");
        return res.json({"status":"error","error":"No such account found"});
    }
    console.log("Deleting "+staff.email);
    await User.deleteOne({_id:uuid});
    return res.json({"status":"ok"});
});

module.exports = deleteS;