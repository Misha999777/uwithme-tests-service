import {Question} from "./Question";

export class TestSession {

    testId: string;
    userId: string;
    userName: string;
    startTime: Date;
    elapsedTime: number;
    score: number;
    questions: Question[];
    userAnswersByQuestionId: Map<number, string[]>
}