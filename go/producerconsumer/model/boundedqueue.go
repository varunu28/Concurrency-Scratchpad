package model

import (
	"sync"
)

const queueCapacity = 10

type BoundedQueue struct {
	queue    []Task
	mu       sync.Mutex
	items    chan int
	capacity chan int
}

func (q *BoundedQueue) AddTask(task *Task) {
	q.capacity <- 1
	q.mu.Lock()
	q.queue = append(q.queue, *task)
	q.mu.Unlock()
	<-q.items
}

func (q *BoundedQueue) ConsumeTask() string {
	q.items <- 1
	q.mu.Lock()
	task := q.queue[0]
	q.queue = q.queue[1:]
	q.mu.Unlock()
	<-q.capacity
	return task.id
}

func NewBoundedQueue() *BoundedQueue {
	return &BoundedQueue{
		queue:    make([]Task, 0),
		items:    make(chan int, 0),
		capacity: make(chan int, queueCapacity),
	}
}
