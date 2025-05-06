import bcrypt from "bcryptjs";
import Employee from "../modals/modal.employee.js";

// Register
async function register(req, res) {
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

  try {
    // Check if email or id already exists
    const existingEmployee = await Employee.findOne({
      $or: [{ email }, { id }],
    });
    if (existingEmployee) {
      return res.status(400).json({ message: "Email or ID already in use" });
    }

    // Hash password
    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);

    const employee = new Employee({
      name,
      email,
      id,
      password: hashedPassword,
      profilePicture,
      phone,
      position,
      shift,
      status,
      isAdmin,
    });

    const savedEmployee = await employee.save();
    return res
      .status(201)
      .json({ message: "User created successfully", employee: savedEmployee });
  } catch (error) {
    return res.status(400).json({ message: "Error creating user", error });
  }
}

// Login
async function login(req, res) {
  const { email, password } = req.body;

  try {
    const employee = await Employee.findOne({ email });
    if (!employee) {
      return res.status(404).json({ message: "User not found" });
    }

    const isMatch = await bcrypt.compare(password, employee.password);
    if (!isMatch) {
      return res.status(401).json({ message: "Invalid credentials" });
    }

    return res.status(200).json({ message: "Login successful", employee });
  } catch (error) {
    return res.status(500).json({ message: "Login error", error });
  }
}

//get one employee

async function getOneEmployee(req, res) {
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
}

//update employee

async function updateEmployee(req, res) {
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
}

//delete employee

async function deleteEmployee(req, res) {
  const { id } = req.params;

  try {
    const employee = await Employee.findOneAndDelete({ id });
    if (!employee) {
      return res.status(404).json({ message: "Employee not found" });
    }
    return res.status(200).json({ message: "Employee deleted successfully" });
  } catch (error) {
    return res.status(500).json({ message: "Error deleting employee", error });
  }
}

//clock in

async function clockin(req, res) {
  const { id } = req.params;
  const date = new Date().toISOString().split("T")[0];

  try {
    const employee = await Employee.findOne({ id });

    if (!employee) {
      return res.status(404).json({ message: "Employee not found" });
    }

    const alreadyClockedIn = employee.attendance.find((a) => a.date === date);
    if (alreadyClockedIn) {
      return res.status(400).json({ message: "Already clocked in today" });
    }

    employee.attendance.push({ date, status: "active" });
    employee.status = "active";
    await employee.save();

    res.status(200).json({ message: "Clock-in successful", employee });
  } catch (error) {
    res.status(500).json({ message: "Clock-in failed", error });
  }
}

//clock out

async function clockout(req, res) {
  const { id } = req.params;
  const date = new Date().toISOString().split("T")[0]; // "YYYY-MM-DD"

  try {
    const employee = await Employee.findOne({ id });

    if (!employee) {
      return res.status(404).json({ message: "Employee not found" });
    }

    const today = employee.attendance.find((a) => a.date === date);
    if (!today) {
      return res.status(400).json({ message: "You haven't clocked in today" });
    }

    today.status = "on leave";
    employee.status = "on leave"; // update current status
    await employee.save();

    res.status(200).json({ message: "Clock-out successful", employee });
  } catch (error) {
    res.status(500).json({ message: "Clock-out failed", error });
  }
}

//assign shifts

async function assignShift(req, res) {
  const { id } = req.params;
  const { shift } = req.body;
  //expected values are "morning" | "afternoon" | "night" | null(delete) only. for shift

  try {
    const employee = await Employee.findOneAndUpdate(
      { id },
      { shift },
      { new: true }
    );

    if (!employee) {
      return res.status(404).json({ message: "Employee not found" });
    }

    return res
      .status(200)
      .json({ message: "Shift assigned successfully", employee });
  } catch (error) {
    return res.status(500).json({ message: "Error assigning shift", error });
  }
}

//get assgined shift for single employee

async function getAssignedShift(req, res) {
  const { id } = req.params;

  try {
    const employee = await Employee.findOne({ id });

    if (!employee) {
      return res.status(404).json({ message: "Employee not found" });
    }

    return res.status(200).json({ shift: employee.shift });
  } catch (error) {
    return res.status(500).json({ message: "Error retrieving shift", error });
  }
}

//get all assigned shifts

async function getAllAssignedShifts(req, res) {
  try {
    const employees = await Employee.find({}, "name shift");

    if (!employees) {
      return res.status(404).json({ message: "No employees found" });
    }

    return res.status(200).json(employees);
  } catch (error) {
    return res.status(500).json({ message: "Error retrieving shifts", error });
  }
}

//get all employees with status

async function getAllEmployeesWithStatus(req, res) {
  try {
    const employees = await Employee.find({}, "name status");

    if (!employees) {
      return res.status(404).json({ message: "No employees found" });
    }

    return res.status(200).json(employees);
  } catch (error) {
    return res
      .status(500)
      .json({ message: "Error retrieving employees", error });
  }
}

//get all employees with attendance

async function getAllEmployeesWithAttendance(req, res) {
  try {
    const employees = await Employee.find({}, "name attendance");

    if (!employees) {
      return res.status(404).json({ message: "No employees found" });
    }

    return res.status(200).json(employees);
  } catch (error) {
    return res
      .status(500)
      .json({ message: "Error retrieving employees", error });
  }
}

export {
  register,
  login,
  getAllEmployees,
  getOneEmployee,
  updateEmployee,
  deleteEmployee,
  clockin,
  clockout,
  assignShift,
  getAssignedShift,
  getAllAssignedShifts,
  getAllEmployeesWithStatus,
  getAllEmployeesWithAttendance,
};
