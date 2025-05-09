import mongoose from "mongoose";

const employeeSchema = new mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
      trim: true,
    },
    email: {
      type: String,
      required: true,
      unique: true,
      trim: true,
    },
    id: {
      type: Number,
      required: true,
      unique: true,
      trim: true,
    },
    password: {
      type: String,
      required: true,
      trim: true,
    },
    profilePicture: {
      type: String,
      default: "default.jpg",
    },
    phone: {
      type: String,
      required: true,
      trim: true,
    },
    position: {
      type: String,
      required: true,
      trim: true,
    },
    shift: {
      type: String,
      enum: ["morning", "afternoon", "night", "null"],
      default: "null",
      required: true,
      trim: true,
    },
    status: {
      type: String,
      enum: ["active", "inactive", "on leave"],
      default: "inactive",
    },
    isAdmin: {
      type: Boolean,
      default: false,
    },
    attendance: [
      {
        date: {
          type: String,
          required: true,
        },
        status: {
          type: String,
          enum: ["active", "on leave", "inactive"],
          required: true,
        },
      },
    ],
  },
  { timestamps: true }
);

const Employee = mongoose.model("Employee", employeeSchema);
export default Employee;

