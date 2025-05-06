// routes/employee.routes.js
import express from "express";

import {
  clockin,
  clockout,
  deleteEmployee,
  getAllEmployees,
  getOneEmployee,
  login,
  register,
  updateEmployee,
} from "./routes.js";

const router = express.Router();

// Register
router.post("/register", register);

// Login
router.post("/login", login);

// Get all
router.get("/", getAllEmployees);

// Get one
router.get("/:id", getOneEmployee);

// Update
router.put("/:id", updateEmployee);

// Delete
router.delete("/:id", deleteEmployee);

//clock in
router.post("/clockin/:id", clockin);

//clock out
router.post("/:id/clockout", clockout);

export default router;
