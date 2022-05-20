const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const feedbackRequest = require('../model/feedback');

const feedbackReq = express.Router();
feedbackReq.use(bodyParser.json());

feedbackReq.post('/',async (req,res)=>{
    const dustbin_id = req.body.dustbin_id;
    const feedback = req.body.feedback;
    const uuid = req.body.uuid;
    console.log(uuid);
    console.log("Adding feedback");
    try{
        const resp = await feedbackRequest.create({
            uuid,
            dustbin_id,
            feedback
        });
        console.log("Feedback added successfully by user ",uuid);
        res.json({status:"ok"});
    }catch(err){
        console.log("Error in creating a feedback");
        return res.json({status:'error',error:'Error in creating a feedback!'});
    }

});

module.exports = feedbackReq;