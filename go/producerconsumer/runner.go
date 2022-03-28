package main

import (
	"Concurrency-Scratchpad/go/producerconsumer/model"
	"sync"
)

func main() {
	var wg sync.WaitGroup

	taskQueue := model.NewBoundedQueue()
	producerOne := model.NewProducer(taskQueue)
	producerTwo := model.NewProducer(taskQueue)
	consumerOne := model.NewConsumer(taskQueue)
	consumerTwo := model.NewConsumer(taskQueue)
	wg.Add(1)

	go producerOne.Produce()
	go producerTwo.Produce()
	go consumerOne.Consume()
	go consumerTwo.Consume()

	wg.Wait()
}
