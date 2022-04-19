import {Component} from '@angular/core';

@Component({
    selector: 'testsystem-paginator',
    templateUrl: './paginator.component.html',
    styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent {

    pages: number = 10;
    currentPage: number;

    setPage(page: number) {
        this.currentPage = page;
    }
}