namespace TodoMVC.Server
{
  using Nancy;

  public class TodoMVCModule : NancyModule
  {
    public TodoMVCModule()
    {
      Get["/index"] = _ => View["Content/index.htm"];
    }
  }
}