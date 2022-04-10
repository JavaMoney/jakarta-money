/*
 *    Copyright 2021 Otavio Santana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package one.microstream.cdi.extension;

import java.util.Map;
import java.util.Set;

public class MediaUser {

    private final String user;

    private final Set<String> medias;

    private final Map<String, String> postsBySocialMedia;

    public MediaUser(String user, Set<String> medias, Map<String, String> postsBySocialMedia) {
        this.user = user;
        this.medias = medias;
        this.postsBySocialMedia = postsBySocialMedia;
    }

    public String getUser() {
        return user;
    }

    public Set<String> getMedias() {
        return medias;
    }

    public Map<String, String> getPostsBySocialMedia() {
        return postsBySocialMedia;
    }

    @Override
    public String toString() {
        return "MediaUser{" +
                "user='" + user + '\'' +
                ", medias=" + medias +
                ", postsBySocialMedia=" + postsBySocialMedia +
                '}';
    }
}
