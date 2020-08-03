CREATE TABLE IF NOT EXISTS todo(
	id INT AUTO_INCREMENT PRIMARY KEY,
	task_name VARCHAR(500) NOT NULL,
	priority INT NOT NULL,
	expected_date DATE NOT NULL
);

INSERT INTO todo (task_name,priority,expected_date) VALUES('Complete task A', 3, '2020-08-08');