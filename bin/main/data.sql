CREATE TABLE IF NOT EXISTS task(
	id INT AUTO_INCREMENT PRIMARY KEY,
	task_name VARCHAR(500) NOT NULL,
	priority INT NOT NULL,
	status VARCHAR(20) NOT NULL,
	expected_date DATE NOT NULL,
	parent_id INT
);

ALTER TABLE task ADD CONSTRAINT TASK_FK FOREIGN KEY (parent_id) REFERENCES task (id);

INSERT INTO task (task_name,priority,status,expected_date, parent_id) VALUES('Complete task A', 3,'progress', '2020-08-08', null);