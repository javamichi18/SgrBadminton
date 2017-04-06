/**
 * Created by Max on 29.03.2017.
 */
import { Component, OnInit } from '@angular/core';
import { SpielerService } from './spieler.service';
import { Spieler } from './spieler';
import {Observable} from 'rxjs';

@Component({
    selector: 'app-ranking',
    templateUrl: './ranking.component.html',
    providers: [ SpielerService ]
})

export class RankingComponent implements OnInit {
    private values: Observable<Spieler[]>;
    private rang: Observable<number[]>;
    private fordern: Observable<number[]>;
    private spielerArray: Spieler[] = [];
    private KannFordern: number[] = [];
    private meinRang: number;

    ngOnInit() {
        this.values = this.spielerservice.obsSpielerholen();
        this.rang = this.spielerservice.obsRangholen();
        this.fordern = this.spielerservice.obskannFordernholen();
    }

    istRangAktuellerSpieler(rang: number, spieler: Spieler): boolean {
        this.AktuelleSpielerRausschreiben(spieler);

        if(this.meinRang === rang)
            return true;
        else
            return false;
    }

    AktuelleSpielerRausschreiben(spieler: Spieler) {
        function f (sp: Spieler, spA: Spieler[]): boolean {

            for (let i = 0; i < spA.length; i++) {
                if (spA[i] === sp) {
                    return false;
                }
            }
            return true;
        }
        if (f(spieler, this.spielerArray) === true) {
            this.spielerArray[this.spielerArray.length] = spieler;
        }
    }

    kannFordern(rang: number): boolean {
        for (let i = 0; i < this.KannFordern.length; i++) {
            if (rang === this.KannFordern[i]) {
                return true;
            }
        }
        return false;

    }

    RangRausschreiben(erg: number) {
        this.meinRang = erg;
    }

    FordernRausschreiben(erg: number) {
        this.KannFordern[this.KannFordern.length] = erg;
    }

    getSpielerderReihe(reihe: number) {
        function getSummebis(ende: number){
            let summe: number = 0;
            for (let i = 1; i < ende; i++)
            {
                summe += i ? i : 0;
            }
            return summe;
        }
        let output: Spieler[] = [];
        let erg = getSummebis(reihe);
        for (let i = erg; i <  erg + reihe; i++) {
            output[output.length] = this.spielerArray[i];
        }
        return output;
    }

    constructor (private spielerservice: SpielerService) {
    }
}
