package com.example

import scala.xml.XML

// Case class to represent an Employee
case class EmployeeStore(
  id: String,
  name: String,
  age: Int,
  position: String,
  department: String,
  salary: Double
)

object EmployeeXMLParser extends App {
  // Read and parse the XML file
  // Note: Replace "employees.xml" with your actual file path
  val xmlData = XML.loadFile("test-data/employees.xml")

  // Parse all employees
  val employees = (xmlData \\ "employee").map { employeeNode =>
    EmployeeStore(
      id = (employeeNode \ "@id").text,
      name = (employeeNode \\ "name").text,
      age = (employeeNode \\ "age").text.toInt,
      position = (employeeNode \ "position").text,
      department = (employeeNode \ "department").text,
      salary = (employeeNode \ "salary").text.toDouble
    )
  }

  // Print all employees
  println("All Employees:")
  println("=============")
  employees.foreach { emp =>
    println(s"""
      |ID: ${emp.id}
      |Name: ${emp.name}
      |Age: ${emp.age}
      |Position: ${emp.position}
      |Department: ${emp.department}
      |Salary: ${emp.salary}
      |-------------""".stripMargin)
  }

  // Some example analytics
  println("\nSummary Statistics:")
  println("=================")
  println(s"Total number of employees: ${employees.size}")
  println(s"Average age: ${employees.map(_.age).sum / employees.size.toDouble}")
  println(s"Average salary: ${employees.map(_.salary).sum / employees.size}")

  // Group by department
  println("\nEmployees by Department:")
  println("=====================")
  employees.groupBy(_.department).foreach { case (dept, emps) =>
    println(s"$dept: ${emps.map(_.name).mkString(", ")}")
  }
}