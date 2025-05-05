import express from "express";
import dotenv from "dotenv";
import connectDB from "./utils/connect.db.js";
import employeeRoutes from "./routes/employees.route.js";

dotenv.config();
const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Use the routes (prefix with /api/employees)
app.use("/api/employees", employeeRoutes);

app.listen(3000, connectDB());
