--1
DROP PROCEDURE IF EXISTS InsertNewCustomer;
GO
CREATE PROCEDURE InsertNewCustomer
    @name VARCHAR(255),
    @address VARCHAR(255),
    @category INT
AS
BEGIN
    IF @category BETWEEN 1 AND 10
    INSERT INTO Customer (name, address, category)
    VALUES (@name, @address, @category);
END 


--2.
GO
DROP PROCEDURE IF EXISTS InsertDepartment;
GO
CREATE PROCEDURE InsertDepartment
    @department_number INT,
    @department_data VARCHAR(255)
AS
BEGIN
    INSERT INTO Department (department_number, department_data)
    VALUES (@department_number, @department_data);
END


GO
DROP PROCEDURE IF EXISTS InsertPaintProcess;
GO
--3. add new process, new supervises, and type dependent entry with needed information
--WHEN doing the java part ask for an int input to pick one of the types like the general picking
--Paint:
CREATE PROCEDURE InsertPaintProcess
    @process_id INT,
    @process_data VARCHAR(255),
    @department_number INT,
    @paint_type VARCHAR(255),
    @painting_method VARCHAR(255)
AS
BEGIN
    -- Insert a new process
    INSERT INTO Process (process_id, process_data) 
    VALUES (@process_id, @process_data);

    -- Insert a new entry in Supervises
    INSERT INTO Supervises (process_id, department_number) 
    VALUES (@process_id, @department_number);

    INSERT INTO Paint_process (process_id, paint_type, painting_method)
    VALUES (@process_id, @paint_type, @painting_method);
END


GO
DROP PROCEDURE IF EXISTS InsertCutProcess;
GO
--Cut:
CREATE PROCEDURE InsertCutProcess
    @process_id INT,
    @process_data VARCHAR(255),
    @department_number INT,
    @cutting_type VARCHAR(255),
    @machine_type VARCHAR(255)
AS
BEGIN
    -- Insert a new process
    INSERT INTO Process (process_id, process_data) 
    VALUES (@process_id, @process_data);

    -- Insert a new entry in Supervises
    INSERT INTO Supervises (process_id, department_number) 
    VALUES (@process_id, @department_number);

    INSERT INTO Cut_process (process_id, cutting_type, machine_type)
    VALUES (@process_id, @cutting_type, @machine_type);
END


GO
DROP PROCEDURE IF EXISTS InsertFitProcess;
GO
--Fit:
CREATE PROCEDURE InsertFitProcess
    @process_id INT,
    @process_data VARCHAR(255),
    @department_number INT,
    @fit_type VARCHAR(255)
AS
BEGIN
    -- Insert a new process
    INSERT INTO Process (process_id, process_data) 
    VALUES (@process_id, @process_data);

    -- Insert a new entry in Supervises
    INSERT INTO Supervises (process_id, department_number) 
    VALUES (@process_id, @department_number);

    INSERT INTO Fit_process (process_id, fit_type)
    VALUES (@process_id, @fit_type);
END


--4. new assembly, new orders, new contains 1 or more 
--WHEN doing the java part ask whether to add another associated process
GO
DROP PROCEDURE IF EXISTS InsertAssemblyWithProcesses;
GO
CREATE PROCEDURE InsertAssemblyWithProcesses
    @name VARCHAR(255),
    @assembly_details VARCHAR(255),
    @assembly_id INT,
    @date_ordered DATE,
    @process_id INT
AS
BEGIN
    -- Insert into the Assembly table
    INSERT INTO Assembly(assembly_id, date_ordered, assembly_details) 
    VALUES (@assembly_id, @date_ordered, @assembly_details);

    -- Insert into the Orders table
    INSERT INTO Orders(assembly_id, name) 
    VALUES (@assembly_id, @name);

    INSERT INTO Containing(assembly_id, process_id) 
    VALUES (@assembly_id, @process_id);
END



--5. new account, type-account
--Assembly:
GO
DROP PROCEDURE IF EXISTS CreateAssemblyAccount;
GO
CREATE PROCEDURE CreateAssemblyAccount(
    @account_number INT,
    @assembly_id INT,
    @established_date DATE,
    @assembly_costs INT
)
AS
BEGIN
    INSERT INTO Assembly_account (account_number, assembly_id, established_date, assembly_costs)
    VALUES (@account_number, @assembly_id, @established_date, @assembly_costs);
END

GO
DROP PROCEDURE IF EXISTS CreateProcedureAccount;
GO
--process:
CREATE PROCEDURE CreateProcedureAccount(
    @account_number INT,
    @process_id INT,
    @established_date DATE,
    @process_costs INT
)
AS
BEGIN
    INSERT INTO Process_account (account_number, process_id, established_date, process_costs)
    VALUES (@account_number, @process_id, @established_date, @process_costs);
END


--Department:
GO
DROP PROCEDURE IF EXISTS CreateDepartmentAccount;
GO
CREATE PROCEDURE CreateDepartmentAccount(
    @account_number INT,
    @department_number INT,
    @established_date DATE,
    @department_costs INT
)
AS
BEGIN
    INSERT INTO Department_account (account_number, department_number, established_date, department_costs)
    VALUES (@account_number, @department_number, @established_date, @department_costs);
END

--6. Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced
--Add new job with req info and add new assigned with needed information
GO
DROP PROCEDURE IF EXISTS EnterNewJob;
GO
CREATE PROCEDURE EnterNewJob
    @job_number INT,
    @assembly_id INT,
    @process_id INT,
    @commence_date DATE
AS
BEGIN
    INSERT INTO Job(job_number, commence_date)
    VALUES (@job_number, @commence_date);
    INSERT INTO Assigned(assembly_id, process_id, job_number)
    VALUES (@assembly_id, @process_id, @job_number);
END


GO
--7. add completion date to an already existing job and add needed information based on type
--WHEN doing the java part ask for an int input to pick one of the types like the general picking
--Paint:
DROP PROCEDURE IF EXISTS CompletePaintJob;
GO
CREATE PROCEDURE CompletePaintJob
    @job_number INT,
    @completion_date DATE,
    @color VARCHAR(255),
    @volume INT,
    @labor_time INT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Job WHERE job_number = @job_number)
    BEGIN
        -- Job exists, update completion information
        UPDATE Job
        SET completion_date = @completion_date
        WHERE job_number = @job_number;

        INSERT INTO Paint_job (job_number, color, volume, labor_time)
        VALUES (@job_number, @color, @volume, @labor_time);
        
    END
    ELSE
    BEGIN
        -- Job doesn't exist, create a new record
        INSERT INTO Job (job_number, commence_date, completion_date)
        VALUES (@job_number, NULL, @completion_date);

        INSERT INTO Paint_job (job_number, color, volume, labor_time)
        VALUES (@job_number, @color, @volume, @labor_time);
    END
END



GO
--Cut:
DROP PROCEDURE IF EXISTS CompleteCutJob;
GO
CREATE PROCEDURE CompleteCutJob
    @job_number INT,
    @completion_date DATE,
    @machine_type VARCHAR(255),
    @time_used INT,
    @material_used VARCHAR(255),
    @labor_time INT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Job WHERE job_number = @job_number)
    BEGIN
        -- Job exists, update completion information
        UPDATE Job
        SET completion_date = @completion_date
        WHERE job_number = @job_number;

        INSERT INTO Cut_job (job_number, machine_type, time_used, material_used, labor_time)
        VALUES (@job_number, @machine_type, @time_used, @material_used, @labor_time);
    END
    ELSE
    BEGIN
        -- Job doesn't exist, create a new record
        INSERT INTO Job (job_number, commence_date, completion_date)
        VALUES (@job_number, NULL, @completion_date);

        INSERT INTO Cut_job (job_number, machine_type, time_used, material_used, labor_time)
        VALUES (@job_number, @machine_type, @time_used, @material_used, @labor_time);
    END
END



GO
--Fit:
DROP PROCEDURE IF EXISTS CompleteFitJob;
GO
CREATE PROCEDURE CompleteFitJob
    @job_number INT,
    @completion_date DATE,
    @labor_time INT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Job WHERE job_number = @job_number)
    BEGIN
        -- Job exists, update completion information
        UPDATE Job
        SET completion_date = @completion_date
        WHERE job_number = @job_number;

        INSERT INTO Fit_job (job_number, labor_time)
        VALUES (@job_number, @labor_time);

    END
    ELSE
    BEGIN
        -- Job doesn't exist, create a new record
        INSERT INTO Job (job_number, commence_date, completion_date)
        VALUES (@job_number, NULL, @completion_date);

        INSERT INTO Fit_job (job_number, labor_time)
        VALUES (@job_number, @labor_time);
    END
END

GO
--8. add new Cost_transactions, and then search for associated department ids and edit their costs
DROP PROCEDURE IF EXISTS UpdateCosts;
GO
CREATE PROCEDURE UpdateCosts
    @transaction_number INT,
    @sup_cost INT,
    @department_account_number INT,
    @process_account_number INT,
    @assembly_account_number INT
AS
BEGIN

    -- Insert into Cost_transactions
    INSERT INTO Cost_transactions (transaction_number, sup_cost, assembly_account_number, department_account_number, process_account_number)
    VALUES (@transaction_number, @sup_cost, @assembly_account_number, @department_account_number, @process_account_number);

    -- Update Department Account
    UPDATE Department_account
    SET department_costs = department_costs + @sup_cost
    WHERE account_number = @department_account_number;

    -- Update Process Account
    UPDATE Process_account
    SET process_costs = process_costs + @sup_cost
    WHERE account_number = @process_account_number;

    -- Update Assembly Account
    UPDATE Assembly_account
    SET assembly_costs = assembly_costs + @sup_cost
    WHERE account_number = @assembly_account_number;
END



GO
--9. Retrieve the total cost incurred on an assembly id
DROP PROCEDURE IF EXISTS GetTotalCostForAssembly;
GO 
CREATE PROCEDURE GetTotalCostForAssembly
    @assembly_id INT
AS
DECLARE @totalcost INT = 0 ;
BEGIN
    SELECT assembly_costs
    FROM Assembly_account
    WHERE assembly_id = @assembly_id;
END


--10. 
GO
DROP PROCEDURE IF EXISTS GetDepartmentLaborTime;
GO 
CREATE PROCEDURE GetDepartmentLaborTime
    @department_number INT,
    @completion_date DATE
AS
BEGIN

    DECLARE @total_labor_time INT = 0;
    SELECT @total_labor_time += labor_time
    FROM Paint_job
    WHERE job_number IN (
        SELECT job_number
        FROM Job
        WHERE job_number IN (
            SELECT job_number
            FROM Assigned
            WHERE process_id IN (
                SELECT process_id
                FROM Supervises
                WHERE department_number = @department_number
            )
        )
        AND completion_date = @completion_date
    )

    SELECT @total_labor_time += labor_time
    FROM Cut_job
    WHERE job_number IN (
        SELECT job_number
        FROM Job
        WHERE job_number IN (
            SELECT job_number
            FROM Assigned
            WHERE process_id IN (
                SELECT process_id
                FROM Supervises
                WHERE department_number = @department_number
            )
        )
        AND completion_date = @completion_date
    )

    SELECT @total_labor_time += labor_time
    FROM Fit_job
    WHERE job_number IN (
        SELECT job_number
        FROM Job
        WHERE job_number IN (
            SELECT job_number
            FROM Assigned
            WHERE process_id IN (
                SELECT process_id
                FROM Supervises
                WHERE department_number = @department_number
            )
        )
        AND completion_date = @completion_date
    );

    SELECT @total_labor_time
END



GO
--check
--11. search through Contains based on assemblyID and find all processIDs associated with it, then join found processes with supervises, to find supervising departments. Return processIDs and departmentIDs
DROP PROCEDURE IF EXISTS GetProcessesForAssembly;
GO 
CREATE PROCEDURE GetProcessesForAssembly
    @assembly_id INT
AS
BEGIN
    -- Select processes and departments for the given assembly_id
    SELECT P.process_id, P.process_data, S.department_number, D.department_data
    FROM Process P
    JOIN Containing C ON P.process_id = C.process_id
    JOIN Supervises S ON P.process_id = S.process_id
    JOIN Department D ON S.department_number = D.department_number
    WHERE C.assembly_id = @assembly_id
    --ORDER BY ;  -- You can use another column for ordering if needed
END


--12. Retrieve the customers (in name order) whose category is in a given range. Use indexing by name?
GO
DROP PROCEDURE IF EXISTS GetCustomersByCategoryRange;
GO 
CREATE PROCEDURE GetCustomersByCategoryRange
    @min_category INT,
    @max_category INT
AS
BEGIN
    -- Select customers within the given category range
    SELECT name, address, category
    FROM Customer
    WHERE category BETWEEN @min_category AND @max_category
    ORDER BY name;
END


GO
--13. Delete all cut-jobs whose job-no is in a given range
DROP PROCEDURE IF EXISTS DeleteCutJobsInRange;
GO 
CREATE PROCEDURE DeleteCutJobsInRange
    @min_job_number INT,
    @max_job_number INT
AS
BEGIN
    DELETE FROM Cut_job
    WHERE job_number BETWEEN @min_job_number AND @max_job_number;
END


GO
DROP PROCEDURE IF EXISTS ChangePaintJobColor;
GO 
--14. Change the color of a given paint job
CREATE PROCEDURE ChangePaintJobColor
    @job_number INT,
    @color VARCHAR(255)
AS
BEGIN
    UPDATE Paint_job
    SET color = @color
    WHERE job_number = @job_number;
END
