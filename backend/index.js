import express from "express";
import dotenv from "dotenv";
import Employee from "./modals/modal.employee.js";
import connectDB from "./utils/connect.db.js";

dotenv.config();

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.post("/api/register", async (req, res) => {
  const {
    name,
    email,
    id,
    password,
    profilePicture,
    phone,
    position,
    shift,
    status,
    isAdmin,
  } = req.body;
  const employee = new Employee({
    name,
    email,
    id,
    password,
    profilePicture,
    phone,
    position,
    shift,
    status,
    isAdmin,
  });
  try {
    const savedEmployee = await employee.save();
    return res
      .status(201)
      .json({ message: "User created successfully", employee: savedEmployee });
  } catch (error) {
    return res.status(400).json({ message: "Error creating user", error });
  }
});

app.post("/api/login", async (req, res) => {
  const { email, password } = req.body;
  const employee = await Employee.findOne({ email });
  if (!employee) {
    return res.status(404).json({ message: "User not found" });
  }
  if (employee.password !== password) {
    return res.status(401).json({ message: "Invalid credentials" });
  }
  return res.status(200).json({ message: "Login successful", employee });
});

app.get("/api/employees", async (req, res) => {
  try {
    const employees = await Employee.find();
    return res.status(200).json(employees);
  } catch (error) {
    return res.status(500).json({ message: "Error fetching employees", error });
  }
});

app.get("/api/employees/:id", async (req, res) => {
  const { id } = req.params;
  try {
    const employee = await Employee.findOne({ id });
    if (!employee) {
      return res.status(404).json({ message: "Employee not found" });
    }
    return res.status(200).json(employee);
  } catch (error) {
    return res.status(500).json({ message: "Error fetching employee", error });
  }
});

app.put("/api/employees/:id", async (req, res) => {
  const { id } = req.params;
  const {
    name,
    email,
    password,
    profilePicture,
    phone,
    position,
    shift,
    status,
    isAdmin,
  } = req.body;
  try {
    const employee = await Employee.findOneAndUpdate(
      { id },
      {
        name,
        email,
        password,
        profilePicture,
        phone,
        position,
        shift,
        status,
        isAdmin,
      },
      { new: true }
    );
    if (!employee) {
      return res.status(404).json({ message: "Employee not found" });
    }
    return res.status(200).json(employee);
  } catch (error) {
    return res.status(500).json({ message: "Error updating employee", error });
  }
});

app.delete("/api/employees/:id", async (req, res) => {
  const { id } = req.params;
  Employee.findOneAndDelete({ id })
    .then((employee) => {
      if (!employee) {
        return res.status(404).json({ message: "Employee not found" });
      }
      return res.status(200).json({ message: "Employee deleted successfully" });
    })
    .catch((error) => {
      return res
        .status(500)
        .json({ message: "Error deleting employee", error });
    });
});

app.listen(3000, connectDB());
