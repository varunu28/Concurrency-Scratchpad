package model

type Task struct {
	id string
}

func (t *Task) PerformTask() string {
	return "Performing task: " + t.id
}
