namespace TodoMVC.Server
{
  using System.Collections.Generic;
  using Nancy;
  using Nancy.ModelBinding;

  public class TodoMVCModule : NancyModule
  {
    private static Dictionary<long, Todo> store = new Dictionary<long, Todo>(); 

    public TodoMVCModule() : base("todos")
    {
      Get["/"] = _ => store.Values;

      Post["/"] = _ =>
        {
          var newTodo = this.Bind<Todo>();
          if (newTodo.id == 0)
            newTodo.id = store.Count + 1;

          if (store.ContainsKey(newTodo.id))
            return HttpStatusCode.NotAcceptable;

          store.Add(newTodo.id, newTodo);
          return newTodo;
        };

      Put["/{id}"] = p =>
        {
          if (!store.ContainsKey(p.id))
            return HttpStatusCode.NotFound;

          var updatedTodo = this.Bind<Todo>();
          store[p.id] = updatedTodo;
          return updatedTodo;
        };

      Delete["/{id}"] = p =>
        {
          if (!store.ContainsKey(p.id))
            return HttpStatusCode.NotFound;

          store.Remove(p.id);
          return HttpStatusCode.OK;
        };
    }
  }

  public class Todo
  {
    public long id { get; set; }
    public string title { get; set; }
    public int order { get; set; }
    public bool completed { get; set; }
  }
}