# Project: VaccNow
##### An application for scheduling and record keeping for Covid-19 vaccination.

## Running the application
- Clone the project.
- Open in Intellij IDEA.
- Update/Download maven dependencies.
- Add a new Run/Debug configuration. (Ref: https://github.com/zain129/vaccNow/blob/master/run_debug_configuration_intellij.png)
- Run the project.
- Use the following examples to consume the APIs.

### REST APIs
##### Get a list of all branches
http://localhost:8081/availability/allBranches

##### Get a list of all available vaccines per branch
http://localhost:8081/availability/branchVaccines

##### Get a specific availability by branches
http://localhost:8081/availability/searchBranchavailability?date=18032020&startTime=1300&endTime=1500

##### Get available time for a branch
http://localhost:8081/availability/branchAvailabilityByDate?branchId=1&date=23032021

##### Schedule vaccination timeslot (15 minutes)
http://localhost:8081/vaccination/scheduleSlot?branchId=1&patientId=1&vaccineId=1&date=23032021&startTime=1125

##### Choose Payment Method
http://localhost:8081/vaccination/payment?scheduleId=5&paymentMethod=Cash&accountNumber=123

##### Confirm scheduled vaccination by email
http://localhost:8081/vaccination/sendEmail

##### Get a list of all applied vaccination per branch
http://localhost:8081/reporting/vaccPerBranch

##### Get a list of all applied vaccination per day/period
- _if endDate is not set then vaccination per day is consumed_

http://localhost:8081/reporting/vaccPerDayPeriod?startDate=23032021
http://localhost:8081/reporting/vaccPerDayPeriod?startDate=21032021&endDate=23032021

##### Show all confirmed vaccinations over a time period
http://localhost:8081/reporting/confirmVaccOverPeriod?startDate=21032021&endDate=23032021
