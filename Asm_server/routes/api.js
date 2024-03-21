var express = require("express");
var router = express.Router();
// them model
const Students = require("../models/students");


// api
router.get("/get-st", async (req, res) => {
    try {
        const data = await Students.find()
        res.send(data);
    } catch (error) {
        console.log(error)
    }
})

// const Upload = require("../config/common/upload");
// router.post("/add-st", Upload.single("avatar"), async(req, res) =>{
//     try {
//         const data = req.body;
//         const {file} = req
//         const newSt = Students({
//             name: data.name,
//             msv: data.msv,
//             diemTb: data.diemTb,
//             avatar: `${req.protocol}://${req.get("host")}/uploads/${file.filename}`,
//         });
//         const result = await newSt.save();
//         if(result){
//             res.json({
//                 "status": 200,
//                 "messenger": "Them thanh cong",
//                 "data": result
//             })

//         } else {
//             res.json({
//                 "status": 400,
//                 "messenger": "Loi, them khong thanh cong",
//                 "data": []
//             })
//         }
//     } catch (error) {
//         console.log(error)
//     }
// })

router.post("/add-st", async (req, res) => {
    try {
        const data = req.body;
        const newSt = Students({
            name: data.name,
            msv: data.msv,
            diemTb: data.diemTb,
            avatar: data.avatar,
        });
        const result = await newSt.save();
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Them thanh cong",
                "data": result
            })

        } else {
            res.json({
                "status": 400,
                "messenger": "Loi, them khong thanh cong",
                "data": []
            })
        }
    } catch (error) {
        console.log(error)
    }
});
router.put("/update-st/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body; 
        const updateSt = await Students.findById(id)
        let result = null;
        if(updateSt){
            updateSt.name = data.name ?? updateSt.name;
            updateSt.msv = data.msv ?? updateSt.msv;
            updateSt.diemTb = data.diemTb ?? updateSt.diemTb;
            updateSt.avatar = data.avatar ?? updateSt.avatar;
            result = await updateSt.save();
        }
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Cap nhat thanh cong",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Loi, cap nhat khong thanh cong",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
})
router.delete("/delete-st/:id", async (req, res) => {
    try {
        const { id } = req.params
        const result = await Students.findByIdAndDelete(id);
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Xoa thanh cong",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Loi, xoa khong thanh cong",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
})

module.exports = router;