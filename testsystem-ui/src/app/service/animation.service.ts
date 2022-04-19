import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent} from '@angular/common/http'
import {Observable} from 'rxjs';
import {finalize} from 'rxjs/operators';
import {NgxSpinnerService} from "ngx-spinner";

@Injectable({
    providedIn: 'root'
})
export class AnimationService implements HttpInterceptor {

    requestCounter = 0;

    constructor(private spinner: NgxSpinnerService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.beginRequest();
        return next.handle(req).pipe(
            finalize(() => {
                this.endRequest();
            })
        );
    }

    beginRequest() {
        this.requestCounter = Math.max(this.requestCounter, 0) + 1;
        if (this.requestCounter === 1) {
            this.spinner.show()
                .catch(() => console.log("Can't show spinner"));
        }
    }

    endRequest() {
        this.requestCounter = Math.max(this.requestCounter, 1) - 1;
        if (this.requestCounter === 0) {
            this.spinner.hide()
                .catch(() => console.log("Can't hide spinner"));
        }
    }
}