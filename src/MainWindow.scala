import scala.swing._, UI._;

//class MainWindow(ui:UI) extends SimpleSwingApplication
//{
//  def top = ui;
//}

class MainWindow(ui:CustomUI) extends SimpleSwingApplication
{
    def top = ui;
}

object program
{
    /**
     * cmdLineArgs:
        * --cmd
            * Starts program in command prompt mode instead of GUI mode.
     */
    def main(args: Array[String])
    {
        if (args.length > 0)
        {
            if (args(0).equals("--cmd"))
            {
                import CommandLineTranslator._;
                ListenToInputs();
            }
            else
            {
                throw new IllegalArgumentException(args(0) + " is not a recognized command line argument. Try '--cmd'");
            }
        }
        else
        {
            var myUI = new MainWindow(new CustomUI());
            myUI.top.visible = true;
        }
    }
}