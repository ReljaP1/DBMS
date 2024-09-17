CREATE TABLE Customer (
    name VARCHAR(255),
    address VARCHAR(255),
    category INT CHECK (category BETWEEN 1 AND 10),
    PRIMARY KEY (name)
);

CREATE TABLE Assembly (
    assembly_id INT,
    date_ordered DATE,
    assembly_details VARCHAR(255),
    PRIMARY KEY (assembly_id)
);


CREATE TABLE Orders (
    assembly_id INT,
    name VARCHAR(255),
    PRIMARY KEY (assembly_id),
    FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id),
    FOREIGN KEY (name) REFERENCES Customer(name)
);

CREATE TABLE Process (
    process_id INT,
    process_data VARCHAR(255),
    PRIMARY KEY (process_id)
);

CREATE TABLE Containing (
    assembly_id INT,
    process_id INT,
    PRIMARY KEY (assembly_id, process_id),
    FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id),
    FOREIGN KEY (process_id) REFERENCES Process(process_id)
);

CREATE TABLE Paint_process (
    process_id INT,
    paint_type VARCHAR(255),
    painting_method VARCHAR(255),
    PRIMARY KEY (process_id),
    FOREIGN KEY (process_id) REFERENCES Process(process_id)
);
CREATE TABLE Fit_process (
    process_id INT,
    fit_type VARCHAR(255),
    PRIMARY KEY (process_id),
    FOREIGN KEY (process_id) REFERENCES Process(process_id)
);

CREATE TABLE Cut_process (
    process_id INT,
    cutting_type VARCHAR(255),
    machine_type VARCHAR(255),
    PRIMARY KEY (process_id),
    FOREIGN KEY (process_id) REFERENCES Process(process_id)
);

CREATE TABLE Department (
    department_number INT,
    department_data VARCHAR(255),
    PRIMARY KEY (department_number)
);

CREATE TABLE Supervises (
    process_id INT,
    department_number INT,
    PRIMARY KEY (process_id),
    FOREIGN KEY (process_id) REFERENCES Process(process_id),
    FOREIGN KEY (department_number) REFERENCES Department(department_number)
);

CREATE TABLE Job (
    job_number INT,
    commence_date DATE,
    completion_date DATE,
    PRIMARY KEY (job_number)
);

CREATE TABLE Paint_job (
    job_number INT,
    color VARCHAR(255),
    volume INT,
    labor_time INT,
    PRIMARY KEY (job_number),
    FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE Fit_job (
    job_number INT,
    labor_time INT,
    PRIMARY KEY (job_number),
    FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE Cut_job (
    job_number INT,
    machine_type VARCHAR(255),
    time_used INT,
    material_used VARCHAR(255),
    labor_time INT,
    PRIMARY KEY (job_number),
    FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE Assigned (
    assembly_id INT,
    process_id INT,
    job_number INT,
    PRIMARY KEY (assembly_id, process_id),
    FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id),
    FOREIGN KEY (process_id) REFERENCES Process(process_id),
    FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE Cost_transactions (
    transaction_number INT,
    sup_cost INT,
    assembly_account_number INT,
    department_account_number INT,
    process_account_number INT,
    PRIMARY KEY (transaction_number)
);

CREATE TABLE Assembly_account (
    account_number INT,
    established_date DATE,
    assembly_costs INT,
    assembly_id INT,
    PRIMARY KEY (account_number),
    FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id)
);

CREATE TABLE Department_account (
    account_number INT,
    established_date DATE,
    department_costs INT,
    department_number INT,
    PRIMARY KEY (account_number),
    FOREIGN KEY (department_number) REFERENCES Department(department_number)
);

CREATE TABLE Process_account (
    account_number INT,
    established_date DATE,
    process_costs INT,
    process_id INT,
    PRIMARY KEY (account_number),
    FOREIGN KEY (process_id) REFERENCES Process(process_id)
);

CREATE TABLE Records (
    transaction_number INT,
    job_number INT,
    PRIMARY KEY (transaction_number),
    FOREIGN KEY (transaction_number) REFERENCES Cost_transactions(transaction_number),
    FOREIGN KEY (job_number) REFERENCES Job(job_number)
);
