package model

import "fmt"

type Consumer struct {
	taskQueue *BoundedQueue
}

func (c *Consumer) Consume() {
	for {
		fmt.Println("Consuming: ", c.taskQueue.ConsumeTask())
	}
}

func NewConsumer(taskQueue *BoundedQueue) *Consumer {
	return &Consumer{taskQueue: taskQueue}
}
