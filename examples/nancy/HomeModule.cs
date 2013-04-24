namespace TodoMVC.Server
{
  using Nancy;

  public class HomeModule : NancyModule
  {
    public HomeModule()
    {
      Get["/"] = _ => View["index.htm"];
    }
  }
}