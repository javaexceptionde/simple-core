/*
 * Copyright  (c) 2021.  Jonathan Bull Contact at jonathan@jbull.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jbull.simplecore.player;

public interface ICorePlayer {

    void setName(String name);

    void setCoins(int coins);

    void addCoins(int coins);

    int getCoins();

    void removeCoins(int coins);

    int getTokens();

    void setTokens(int tokens);

    void addTokens(int tokens);

    void removeTokens(int tokens);


}
