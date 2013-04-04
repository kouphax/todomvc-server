module.exports.store = (function(){

  var cache = {};
  var nextId = 0;

  return {
    save: function(data) {
      var id = ++nextId;
      var todo = {
        id: id,
        title: data.title,
        completed: data.completed,
        order: data.order
      }

      cache[id] = todo;

      return todo;
    },

    getAll: function(){
      var todos = []
      for(var key in cache) if(cache.hasOwnProperty(key)) {
          todos.push(cache[key]);
      }
      return todos;
    },

    update: function(id, data) {
      if(cache[id]) {
        var todo = {
          id: id,
          title: data.title,
          completed: data.completed,
          order: data.order
        }

        cache[id] = todo;

        return todo;
      }
    },

    delete: function(id){
      delete cache[id]
    }
  }

})();