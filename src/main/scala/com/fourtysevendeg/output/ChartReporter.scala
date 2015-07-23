package com.fourtysevendeg.jsonScalaPerftest.output

//import java.awt.BorderLayout
import java.awt.event.WindowEvent
//import javax.swing.JFrame

import org.jfree.data.category.{CategoryDataset, DefaultCategoryDataset}
import org.jfree.ui.{RefineryUtilities, ApplicationFrame}
import org.jfree.chart.{ChartUtilities, ChartFactory, JFreeChart, ChartPanel}
//import scala.Predef._
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.axis.{CategoryAxis, CategoryLabelPositions, NumberAxis}
import org.jfree.chart.renderer.category.{StandardBarPainter, BarRenderer}
//import java.awt.image.BufferedImage
import java.io.{File, FileOutputStream}
//import java.awt.geom.Rectangle2D
//import com.sun.image.codec.jpeg.JPEGCodec

class ChartReporter(operation:String, title: String, selectDataSet: String,
                    results: Map[String, Map[String, Long]]) extends ApplicationFrame(title) {

  import java.awt.Color
  import java.awt.Dimension

  override def windowClosing(evt:WindowEvent){
    if(evt.getWindow() == this){
      dispose();
    }
  }
  val graphDataSet = createDataset()

  val chart = createChart(graphDataSet)
  val chartPanel = new ChartPanel(chart)
  chartPanel.setPreferredSize(new Dimension(1000, 400))
  setContentPane(chartPanel)

  def createDataset(): DefaultCategoryDataset = {
    val categoryDataSet = new DefaultCategoryDataset()

    for ((dataset, info) <- results) {
      for ((adaptor, measurement) <- info) {
        if (dataset == selectDataSet) {
          //println(s"$adaptor,$dataset")
          categoryDataSet.addValue(measurement.toDouble / 1000000.0, adaptor, "")
        }
      }
    }
    categoryDataSet
  }

  def createChart(dataset: CategoryDataset): JFreeChart = {

    // create the chart...
    val chart = ChartFactory.createBarChart(
      s"$title $operation $selectDataSet", // chart title
      "", // domain axis label
      "Time (ms)", // range axis label
      dataset, // data
      PlotOrientation.VERTICAL, // orientation
      true, // include legend
      true, // tooltips?
      false // URLs?
    )

    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

    // set the background color for the chart...
    chart.setBackgroundPaint(Color.white)

    // get a reference to the plot for further customisation...
    val plot = chart.getCategoryPlot()
    plot.setBackgroundPaint(Color.lightGray)
    plot.setDomainGridlinePaint(Color.white)
    plot.setRangeGridlinePaint(Color.white)

    // set the range axis to display integers only...
    val rangeAxis = plot.getRangeAxis().asInstanceOf[NumberAxis]
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits())

    val renderer:BarRenderer = plot.getRenderer().asInstanceOf[BarRenderer]
    renderer.setBarPainter(new StandardBarPainter())
    renderer.setDrawBarOutline(true)
    renderer.setShadowVisible(false)
    //renderer.setMaximumBarWidth(100.0)
    //renderer.setItemMargin(0.1)

    //domainAxis.setCategoryMargin(0.1)
    //domainAxis.setLowerMargin(0.1)
    //domainAxis.setUpperMargin(0.1)
    //domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
    //domainAxis.setCategoryLabelPositions(
      //CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4.0)
    //)
    chart
  }


  def view {
    val f = new File(s"charts/$operation-$selectDataSet.jpg")
    ChartUtilities.saveChartAsJPEG(f, chart, 1000, 500)

    this.pack()
    RefineryUtilities.centerFrameOnScreen(this)
    this.setVisible(true)
  }

  /*
  def draw(chart: JFreeChart, width: Int, height: Int): BufferedImage = {

    val img =
      new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val g2 = img.createGraphics()

    chart.draw(g2, new Rectangle2D.Double(0, 0, width, height))


    g2.dispose()
    img
  }

  def saveToFile(
                  aFileName: String,
                  width: Int,
                  height: Int,
                  quality: Double) {
    val img = draw(chart, width, height)

    val fos = new FileOutputStream(aFileName)
    val encoder2 =
      JPEGCodec.createJPEGEncoder(fos)
    val param2 = encoder2.getDefaultJPEGEncodeParam(img)
    param2.setQuality(quality.toFloat, true)
    encoder2.encode(img, param2)
    fos.close()
  }
  */

}
