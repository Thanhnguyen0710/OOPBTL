package mrmathami.thegame.entity.tile.tower;

import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.UpdatableEntity;
import mrmathami.thegame.entity.bullet.AbstractBullet;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.bullet.SniperBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.NormalEnemy;
import mrmathami.thegame.entity.tile.AbstractTile;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static mrmathami.thegame.GameEntities.*;

public abstract class AbstractTower<E extends AbstractBullet> extends AbstractTile implements UpdatableEntity {
	private final double range;
	private final long speed;

	private long tickDown;

	protected AbstractTower(long createdTick, long posX, long posY, double range, long speed) {
		super(createdTick, posX, posY, 1L, 1L);
		this.range = range;
		this.speed = speed;
		this.tickDown = 0;
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		if (tickDown <= 0) {
			// TODO: Find a target and spawn a bullet to that direction.
			// Use GameEntities.getFilteredOverlappedEntities to find target in range
			// Remember to set this.tickDown back to this.speed after shooting something.
			List<AbstractEnemy> list = (List<AbstractEnemy>) GameEntities.getFilteredOverlappedEntities(field.getEntities(),AbstractEnemy.class,getPosX(),getPosY(),range,range);
			for(int i = 0 ; i < list.size() ; i++)
				field.doSpawn(doSpawn(getCreatedTick(),getPosX(),getPosY(),list.get(i).getPosX()-getPosX(),list.get(i).getPosY()-getPosY()));
			this.tickDown = speed;
		}
	}

	/**
	 * Create a new bullet. Each tower spawn different type of bullet.
	 * Override this method and return the type of bullet that your tower shot out.
	 * See NormalTower for example.
	 *
	 * @param createdTick createdTick
	 * @param posX posX
	 * @param posY posY
	 * @param deltaX deltaX
	 * @param deltaY deltaY
	 * @return the bullet entity
	 */
	@Nonnull
	protected abstract E doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY);
}
