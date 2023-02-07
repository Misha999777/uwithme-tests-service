import {Injectable} from "@angular/core";
import {Actions, createEffect, CreateEffectMetadata, ofType} from "@ngrx/effects";
import {catchError, EMPTY, map, mergeMap} from "rxjs";
import {DataService} from "../../service/data.service";
import {reloadTests, setTests} from "./admin.actions";

@Injectable()
export class AdminEffects {

    loadTestsEffect: CreateEffectMetadata;

    constructor(private actions: Actions, private dataService: DataService) {
        this.loadTestsEffect = createEffect(() =>
            this.actions.pipe(
                ofType(reloadTests),
                mergeMap(() =>
                    this.dataService.getTests().pipe(
                        map(tests => (setTests({tests: tests}))),
                        catchError(() => EMPTY)
                    )
                )
            )
        );
    }
}