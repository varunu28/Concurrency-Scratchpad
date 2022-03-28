package model

import (
	"github.com/google/uuid"
)

type Producer struct {
	taskQueue *BoundedQueue
}

func (p *Producer) Produce() {
	for {
		p.taskQueue.AddTask(&Task{uuid.New().String()})
	}
}

func NewProducer(taskQueue *BoundedQueue) *Producer {
	return &Producer{
		taskQueue: taskQueue,
	}
}
