// routes/employee.routes.js
import express from "express";

import {
  assignShift,
  clockin,
  clockout,
  deleteEmployee,
  getAllAssignedShifts,
  getAllEmployees,
  getAllEmployeesWithAttendance,
  getAllEmployeesWithStatus,
  getAssignedShift,
  getOneEmployee,
  login,
  register,
  updateEmployee,
} from "./routes.js";
import Employee from "../modals/modal.employee.js";

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

//assign shift
router.post("/assign-shift/:id", assignShift);

//get assigned shift for a single employee
router.get("/assigned-shift/:id", getAssignedShift);

//get all assigned shifts
router.get("/assigned-shift", getAllAssignedShifts);

//get all Employee with status
router.get("/status", getAllEmployeesWithStatus);

//get all employees with attendance
router.get("/attendance", getAllEmployeesWithAttendance);

export default router;
