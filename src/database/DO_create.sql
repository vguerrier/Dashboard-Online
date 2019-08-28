CREATE TABLE "Case" (
	"caseNumber" VARCHAR2(255) NOT NULL,
	"customerName" VARCHAR2(255) NOT NULL,
	"scorePriority" INT NOT NULL,
	"functionalDomain" VARCHAR2(255) NOT NULL,
	"status" VARCHAR2(255) NOT NULL,
	"module" VARCHAR2(255) NOT NULL,
	"priority" VARCHAR2(255) NOT NULL,
	"EDD" DATE NOT NULL,
	"Owner" VARCHAR2(255) NOT NULL,
	"assignedGroup" VARCHAR2(255) NOT NULL,
	"VTP" VARCHAR2(255) NOT NULL,
	"title" VARCHAR2(255) NOT NULL,
	"workstream" VARCHAR2(255) NOT NULL,
	"creationDate" DATE NOT NULL,
	"lastModificationDate" DATE NOT NULL,
	"customerCaseNumber" VARCHAR2(255) NOT NULL,
	"production" VARCHAR2(255) NOT NULL,
	"CQassignedTo" VARCHAR2(255) NOT NULL,
	"CQlabel" VARCHAR2(255) NOT NULL,
	"CQstate" VARCHAR2(255) NOT NULL,
	"assignedPerson" VARCHAR2(255) NOT NULL,
	"rejectedCount" INT NOT NULL,
	"ECD" DATE NOT NULL,
	"targetPatch" VARCHAR2(255) NOT NULL,
	"IncID" VARCHAR2(255) NOT NULL,
	"version" VARCHAR2(255) NOT NULL,
	"clientnumber" VARCHAR2(255) NOT NULL,
	constraint CASE_PK PRIMARY KEY ("caseNumber"));


/
CREATE TABLE "Card" (
	"numerofiche" VARCHAR2(255) NOT NULL,
	"dbid" INT NOT NULL,
	"titre" VARCHAR2(255) NOT NULL,
	"status" VARCHAR2(255) NOT NULL,
	"casenumber" VARCHAR2(255) NOT NULL,
	"datePlanningLatest" DATE NOT NULL,
	"datePlanningEarliest" DATE NOT NULL,
	"label" VARCHAR2(255) NOT NULL,
	"id" VARCHAR2(255) NOT NULL,
	"rfeId" VARCHAR2(255) NOT NULL,
	"typeReference" VARCHAR2(255) NOT NULL,
	"client" VARCHAR2(255) NOT NULL,
	"domainFunctional" VARCHAR2(255) NOT NULL,
	"loginName" VARCHAR2(255) NOT NULL,
	"productName" VARCHAR2(255) NOT NULL,
	"retrofitCard" INT NOT NULL,
	"openingDate" DATE NOT NULL,
	"ECD" DATE NOT NULL,
	"severity" VARCHAR2(255) NOT NULL,
	"age" INT NOT NULL,
	"subProduct" VARCHAR2(255) NOT NULL,
	"internalState" VARCHAR2(255) NOT NULL,
	"regression" INT NOT NULL,
	"regressionCause" VARCHAR2(255) NOT NULL,
	constraint CARD_PK PRIMARY KEY ("numerofiche"));


/
CREATE TABLE "CaseToCard" (
	"CaseId" VARCHAR2(255) NOT NULL,
	"CardId" VARCHAR2(255) NOT NULL,
	constraint CASETOCARD_PK PRIMARY KEY ("CaseId","CardId"));


/


ALTER TABLE "CaseToCard" ADD CONSTRAINT "CaseToCard_fk0" FOREIGN KEY ("CaseId") REFERENCES "Case"("caseNumber");
ALTER TABLE "CaseToCard" ADD CONSTRAINT "CaseToCard_fk1" FOREIGN KEY ("CardId") REFERENCES "Card"("numerofiche");

