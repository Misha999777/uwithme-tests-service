import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Question} from "../../model/Question";

@Component({
    selector: 'testsystem-question-viewer',
    templateUrl: './question-viewer.component.html',
    styleUrls: ['./question-viewer.component.css']
})
export class QuestionViewerComponent {

    @Input()
    question: Question;
    @Output()
    select = new EventEmitter<number>();

    selectAnswer(index: number) {
        this.select.emit(index);
    }
}