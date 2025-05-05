import Employee from "../modals/modal.employee.js";
//register
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
}

//login

async function login(req, res) {
  const { email, password } = req.body;
  const employee = await Employee.findOne({ email });

  if (!employee) {
    return res.status(404).json({ message: "User not found" });
  }

  if (employee.password !== password) {
    return res.status(401).json({ message: "Invalid credentials" });
  }

  return res.status(200).json({ message: "Login successful", employee });
}

//get all employees

async function getAllEmployees(req, res) {
  try {
    const employees = await Employee.find();
    return res.status(200).json(employees);
  } catch (error) {
    return res.status(500).json({ message: "Error fetching employees", error });
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

export {
  register,
  login,
  getAllEmployees,
  getOneEmployee,
  updateEmployee,
  deleteEmployee,
};
