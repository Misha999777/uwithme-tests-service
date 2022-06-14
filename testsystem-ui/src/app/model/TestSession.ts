import {Question} from "./Question";

export class TestSession {

    testId: string;
    userId: string;
    userName: string;
    startTime: Date;
    durationMinutes: number;
    elapsedTime: number;
    score: number;
    questions: Question[];
    userAnswersByQuestionId: Map<number, number[]>
}