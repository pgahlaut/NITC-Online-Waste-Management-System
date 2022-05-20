const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const bcrypt = require('bcryptjs');

const register = express.Router();
register.use(bodyParser.json());
register.post('/',async (req,res)=>{
    const {email,password:plainTextPassword,name,type} = req.body;
    if(!email|| typeof email!='string'){
        return res.json({status:'error',error:'Invalid email'});
    }
    if(!plainTextPassword|| typeof plainTextPassword!='string'){
        return res.json({status:'error',error:'Invalid password'});
    }
    if(!name|| typeof name!='string'){
        return res.json({status:'error',error:'Invalid name'});
    }
    if(plainTextPassword.length<5){
        return res.json({status:'error',error:'Password should be atleast 5 characters'});
    }
    const password = await bcrypt.hash(plainTextPassword,10);
    
    try{
        const response = await User.create({
            email,
            password,
            name,
            type
        });
        console.log("User created successfully!",response)
    }catch(err){
        if(err.code === 11000){
            return res.json({status:'error',error:'Username already in use'});
        }
        throw err;
    }
    res.json({status:"ok"});
});



module.exports = register; 