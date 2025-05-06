import express from "express";
import dotenv from "dotenv";
import connectDB from "./utils/connect.db.js";
import employeeRoutes from "./routes/employees.route.js";

dotenv.config();
const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use("/api/employees", employeeRoutes);

const port = process.env.PORT || 3000;

app.listen(port, connectDB());
