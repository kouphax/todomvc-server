class Store
  @todos = {}
  @next_id = 0

  class << self
    def save data
      id = @next_id += 1
      todo = {
        id: id,
        title: data.title,
        completed: data.completed,
        order: data.order
      }
      @todos[id] = todo
      todo
    end

    def get_all
      @todos.values
    end

    def update(id, data)
      todo = {
        id: id.to_i,
        title: data.title,
        completed: data.completed,
        order: data.order
      }
      @todos[id.to_i] = todo
      todo
    end

    def get(id)
      @todos[id.to_i]
    end

    def delete(id)
      @todos.delete(id.to_i)
    end

  end
end
