package com.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

//==============================================================
//    <employees>  // Root: top-level element
//        <employee> // Node: any part of XML, Element: Node that has attribute or children
//            <id>1</id>
//            <profile>  // Node and Element
//                <name>John Doe</name>
//                <age>30</age>
//            </profile>
//            <position>Software Engineer</position>
//            <department>IT</department>
//            <salary>80000</salary>
//        </employee>
//
//        <employee>
//            <..>
//        </employee>
//
//     <employees>
//==============================================================

public class xmlParser2 {
    public static void main(String[] args) {
        boolean testMode = true; // TODO: true=testing, false=production
        String filePath = "";

        if (!testMode) {
            if (args.length < 1) {
                System.out.println("Please provide the XML file path as an argument.");
                return;
            }
            else {
                //Get the file path from command-line argument
                filePath = args[0];
            }
        }
        else {
            filePath = "test-data/employees.xml"; // TODO: for testing, hardcode to test-data/employees.xml
        }

        try { //calculateTotalSalary based on department
            String departmentName = "IT"; // TODO: change department name here to test , IT, Marketing

            ArrayList<Integer> salaries = findSalaryByDepartment(filePath, departmentName);
            calculateTotalSalary(departmentName, salaries);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Integer> findSalaryByDepartment(String filePath, String departmentName) throws ParserConfigurationException, IOException, SAXException {
        //create xml dom parser
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //parse xml file
        Document document = builder.parse(filePath);
        NodeList employees = document.getElementsByTagName("employee");

        // salary array
        ArrayList<Integer> salaries = new ArrayList<>();

        // Creating a HashMap/Dictionary
        Map<String, String> dictionary = new HashMap<>();

        //for each employee, list profile and salary based on department
        for (int i = 0; i < employees.getLength(); i++) {
            Node employeeNode = employees.item(i);
            if (employeeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element employee = (Element) employeeNode;
                //list employee details
                ArrayList<Employee> employeeArrayList = listEmployeeDetails(employee);

                // add salary to array
                if (employeeArrayList.get(0).getDepartment().equals(departmentName)) {
                    salaries.add(Integer.parseInt(employeeArrayList.get(0).getSalary()));

                    // update dictionary with name and salary
                    dictionary.put(employeeArrayList.get(0).getName(), employeeArrayList.get(0).getSalary());
                }
            }
        }
        // print dictionary
        System.out.println(dictionary);

        return salaries;
    }
        public static void calculateTotalSalary (String departmentName, ArrayList < Integer > salaries){
            int totalSalary = 0;
            for (int salary : salaries) {
                totalSalary += salary;
            }
            System.out.println("Total salary for " + departmentName + ": " + totalSalary);
        }



        public static ArrayList<Employee> listEmployeeDetails (Element employee){
            // Access the profile element
//        Element profile = (Element) employee.getElementsByTagName("profile").item(0);
            Node profile = employee.getElementsByTagName("profile").item(0);
            Element profileElement = (Element) profile; // convert to Element
            String name = profileElement.getElementsByTagName("name").item(0).getTextContent();
            String age = profileElement.getElementsByTagName("age").item(0).getTextContent();

            // Access the id, position, department, and salary
            String id = employee.getAttribute("id");
            String position = employee.getElementsByTagName("position").item(0).getTextContent();
            String department = employee.getElementsByTagName("department").item(0).getTextContent();
            String salary = employee.getElementsByTagName("salary").item(0).getTextContent();

            // Print employee details
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Position: " + position);
            System.out.println("Department: " + department);
            System.out.println("Salary: " + salary);
            System.out.println("-------------------------------");

            // Add to EmployeeList
            ArrayList<Employee> employeeArrayList = new ArrayList<>();
            employeeArrayList.add(new Employee(id, name, age, position, department, salary));

            return employeeArrayList;
        }
    }










