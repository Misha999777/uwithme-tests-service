import {Question} from "./Question";
import {TestSession} from "./TestSession";

export class Test {

    id: string;
    name: string;
    authorId: string;
    durationMinutes: number;
    questionsNumber: number;
    questions: Question[];
    testSessions: TestSession[];

    constructor(name?: string, durationMinutes?: number, questionsNumber?: number) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.questionsNumber = questionsNumber;
    }
}