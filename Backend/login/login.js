const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const jwt = require('jsonwebtoken')
const bcrypt = require('bcryptjs');
const config = require('./config');

const login = express.Router();
const JWT_SECRET = 'ksjdhgkshfkdshfkdhfkdhfkdsjhfkdj';
login.use(bodyParser.json());
login.post('/',async (req,res)=>{
    const {email,password} = req.body;
    const user = await User.findOne({email}).lean();
    if(!user){
        return res.json({status:'error',error:'Invalid eamil/password'});
    }
    if(await bcrypt.compare(password,user.password)){
        //success
        const token = jwt.sign({id: user._id,email:user.email}, JWT_SECRET);
        var id = user._id;
        console.log(id.toString());
        return res.json({status:'ok',data:token,email:user.email,type:user.type,name:user.name,uuid:id.toString()});
    }
    res.json({"status":"error"});
});



module.exports = login; 