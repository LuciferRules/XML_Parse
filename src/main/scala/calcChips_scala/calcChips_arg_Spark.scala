package calcChips_scala

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.w3c.dom.{Element, NodeList}

import java.net.URI
import javax.xml.parsers.DocumentBuilderFactory
import scala.collection.mutable.HashMap // Import for URI handling

object calcChips_arg_Spark {
  def main(args: Array[String]): Unit = {

    if (args.length < 1) {
      System.err.println("Usage: calcChips_arg <inputPath1> <inputPath2> ...")
      System.exit(1)
    }

    val conf = new SparkConf().setAppName("calcChips_arg")
    val spark = SparkSession.builder.config(conf).getOrCreate()
    val sc = spark.sparkContext

    val inputPaths = args.toSeq

    inputPaths.foreach { inputPathStr =>
      try {
        // Use Hadoop FileSystem API
        val uri = new URI(inputPathStr)
        val path = new Path(uri)
        val fs = FileSystem.get(uri, sc.hadoopConfiguration) // Get FileSystem for the URI

        if (fs.exists(path)) {
          readXML(inputPathStr,sc) // Call readXML method
        } else {
          println(s"File not found: $inputPathStr")
        }


      } catch {
        case e: Exception =>
          System.err.println(s"Error processing $inputPathStr: ${e.getMessage}")
          e.printStackTrace() // Print the full stack trace for debugging
      }
    }
    spark.stop()
  }

  // readXML method
  def readXML(filePath: String, sc: SparkContext): Unit = {

    try {
      /* Step1: Load the XML file and parse */
      val uri = new URI(filePath)
      val path = new Path(uri)
      val fs = FileSystem.get(uri, sc.hadoopConfiguration) // Get FileSystem for the URI

      if (fs.exists(path)) { // Keep this check
        val inputStream = fs.open(path) // Use FileSystem to open the file
        val dbFactory = DocumentBuilderFactory.newInstance()
        dbFactory.setNamespaceAware(true)
        val dB = dbFactory.newDocumentBuilder()
        val doc = dB.parse(inputStream) // Parse from the InputStream

        /* Step2: Get all SubstrateMap elements */
        // Define the namespace URI
        val namespaceURI = "urn:semi-org:xsd.E142-1.V1005.SubstrateMap"
        // Get all SubstrateMap elements based on semi schema
        val substrateMaps = doc.getElementsByTagNameNS(namespaceURI, "SubstrateMap")

        println(substrateMaps.getLength)
        // Iterate over SubstrateMap elements
        for (i <- 0 until substrateMaps.getLength) {
          val substrateMap = substrateMaps.item(i).asInstanceOf[Element]

          // Get the SubstrateType attribute
          val substrateType = substrateMap.getAttribute("SubstrateType")
          println("SubstrateType: " + substrateType)

          // Get the SubstrateId attribute
          val substrateId = substrateMap.getAttribute("SubstrateId")
          println("SubstrateId: " + substrateId)
        }

        /* Step3: Find Wafer id */
        val TransferMap = doc.getElementsByTagNameNS(namespaceURI, "TransferMap")
        println(TransferMap.getLength)

        // Iterate over TransferMap elements
        for (i <- 0 until TransferMap.getLength) {
          val transferMap = TransferMap.item(i).asInstanceOf[Element]
          println(transferMap.getAttribute("FromSubstrateType")) //Wafer
          println(transferMap.getAttribute("FromSubstrateId")) //VC339684.05
        }

        /* Step4: Find T elements and count*/
        val T = doc.getElementsByTagNameNS(namespaceURI, "T")
        println("T length: " + T.getLength)
        // Iterate over TransferMap elements
        for (i <- 0 until T.getLength) {
          val TMap = T.item(i).asInstanceOf[Element]
          print("FX: " + TMap.getAttribute("FX") + " ")
          print("FY: " + TMap.getAttribute("FY") + " ")
          print("TX: " + TMap.getAttribute("TX") + " ")
          print("TY: " + TMap.getAttribute("TY") + " ")
          println() //newline
        }

        findUniqueTxTy(T)

        inputStream.close() // Important: Close the InputStream
      }
    }
    catch {
      case e: Exception => println("Unexpected error occurred while reading XML file." + e)
    }
  }

  private def findUniqueTxTy(T: NodeList): Unit = {
    // Create a HashMap to store the unique TX and TY combinations and their counts
    val txTyCounts = new HashMap[String, Int]

    // Iterate over T elements
    for (i <- 0 until T.getLength) {
      val TMap = T.item(i).asInstanceOf[Element]
      val tx = TMap.getAttribute("TX")
      val ty = TMap.getAttribute("TY")

      // Create a key for the HashMap by combining TX and TY
      val key = tx + "_" + ty

      // Check if the key already exists in the HashMap
      if (txTyCounts.contains(key)) {
        // If it exists, increment the count by 1
        txTyCounts(key) = txTyCounts(key) + 1
      } else {
        // If it doesn't exist, add it to the HashMap with a count of 1
        txTyCounts(key) = 1
      }
    }

    // Print the unique TX and TY combinations and their counts
    for ((key, value) <- txTyCounts) {
      println(s"TX: $key, TY: $value, Count: $value")
    }

    // Print the total number of unique TX and TY combinations
    println(s"Total unique TX and TY combinations: ${txTyCounts.size}")
  }
}
